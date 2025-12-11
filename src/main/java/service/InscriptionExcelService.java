package service;

import model.Utilisateur;
import org.apache.poi.ss.usermodel.*;
import repo.utilisateurRepo;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class InscriptionExcelService {

    private static final String DEFAULT_FOLDER = "src/main/resources/inscritExcel";

    private final UtilisateurService utilisateurService;
    private final utilisateurRepo utilisateurRepo;
    private final DataFormatter dataFormatter = new DataFormatter();

    public InscriptionExcelService() {
        this(new UtilisateurService(), UtilisateurService.utilisateurRepo);
    }

    public InscriptionExcelService(UtilisateurService utilisateurService, utilisateurRepo utilisateurRepo) {
        this.utilisateurService = utilisateurService;
        this.utilisateurRepo = utilisateurRepo;
    }

    /**
     * Lit le fichier Excel situé dans resources/inscritExcel (sauf chemin absolu)
     * et crée les comptes utilisateur correspondants via UtilisateurService.
     *
     * @param excelPath nom de fichier ou chemin complet du classeur
     * @return la liste des utilisateurs créés
     */
    public List<Utilisateur> importerUtilisateurs(String excelPath) throws IOException {
        // Résout le chemin relatif par défaut vers un chemin absolu lisible.
        Path path = resolvePath(excelPath);
        if (!Files.exists(path)) {
            throw new IOException("Fichier Excel introuvable : " + path);
        }

        try (InputStream inputStream = Files.newInputStream(path);
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            // On ne lit que la première feuille (attendue) du classeur.
            Sheet sheet = workbook.getNumberOfSheets() > 0 ? workbook.getSheetAt(0) : null;
            if (sheet == null) {
                return Collections.emptyList();
            }

            Row headerRow = sheet.getRow(sheet.getFirstRowNum());
            Map<String, Integer> headerIndex = mapHeaderIndices(headerRow);

            if (!headerIndex.containsKey("email") || !headerIndex.containsKey("role")) {
                throw new IllegalArgumentException("Le fichier doit contenir au minimum les colonnes Email et Role");
            }

            List<Utilisateur> created = new ArrayList<>();
            for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null || isRowEmpty(row)) {
                    continue;
                }

                // Lecture des données par nom de colonne (case insensitive) puis nettoyage.
                String email = readCell(row, headerIndex.get("email"));
                String role = normalizeRole(readCell(row, headerIndex.get("role")));
                String nom = readCell(row, headerIndex.get("nom"));
                String prenom = readCell(row, headerIndex.get("prenom"));

                if (email == null || role == null) {
                    continue;
                }

                // Ignore si un compte existe déjà avec cet email.
                if (utilisateurRepo.emailExists(email)) {
                    continue;
                }

                // Création du compte puis complétion éventuelle des champs nom/prénom.
                Utilisateur utilisateur = utilisateurService.creerCompteUtilisateur(email, role);
                if (utilisateur == null) {
                    continue;
                }

                boolean updated = false;
                if (nom != null) {
                    utilisateur.setNomUtilisateur(nom);
                    updated = true;
                }
                if (prenom != null) {
                    utilisateur.setPrenomUtilisateur(prenom);
                    updated = true;
                }
                if (updated) {
                    utilisateurRepo.updateUtilisateur(utilisateur);
                }
                created.add(utilisateur);
            }

            System.out.println(created);
            return created;
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new IOException("Erreur lors de la lecture du fichier Excel", e);
        }
    }

    private Path resolvePath(String excelPath) {
        Path path = Paths.get(excelPath);
        if (!path.isAbsolute()) {
            path = Paths.get(DEFAULT_FOLDER).resolve(path);
        }
        return path.normalize();
    }

    private Map<String, Integer> mapHeaderIndices(Row headerRow) {
        Map<String, Integer> map = new HashMap<>();
        if (headerRow == null) {
            return map;
        }
        for (Cell cell : headerRow) {
            String value = dataFormatter.formatCellValue(cell);
            if (value == null) {
                continue;
            }
            switch (value.trim().toLowerCase(Locale.ROOT)) {
                case "nom":
                    map.put("nom", cell.getColumnIndex());
                    break;
                case "prenom":
                    map.put("prenom", cell.getColumnIndex());
                    break;
                case "email":
                    map.put("email", cell.getColumnIndex());
                    break;
                case "role":
                    map.put("role", cell.getColumnIndex());
                    break;
                default:
                    break;
            }
        }
        return map;
    }

    private String readCell(Row row, Integer index) {
        if (row == null || index == null) {
            return null;
        }
        Cell cell = row.getCell(index);
        if (cell == null) {
            return null;
        }
        String value = dataFormatter.formatCellValue(cell);
        if (value == null) {
            return null;
        }
        value = value.trim();
        return value.isEmpty() ? null : value;
    }

    private boolean isRowEmpty(Row row) {
        for (Cell cell : row) {
            String value = dataFormatter.formatCellValue(cell);
            if (value != null && !value.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private String normalizeRole(String role) {
        if (role == null) {
            return null;
        }
        String normalized = role.trim().toLowerCase(Locale.ROOT);
        switch (normalized) {
            case "coach":
            case "joueur":
            case "parent":
            case "secretaire":
                return normalized.substring(0, 1).toUpperCase(Locale.ROOT) + normalized.substring(1);
            default:
                return null;
        }
    }
}
