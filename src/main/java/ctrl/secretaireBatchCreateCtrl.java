package ctrl;

import service.InscriptionExcelService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@MultipartConfig
public class secretaireBatchCreateCtrl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/secretaire/batchCreate.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1) Récupérer la partie multipart envoyée depuis le formulaire.
        Part filePart = request.getPart("excelFile");
        if (filePart == null || filePart.getSize() == 0) {
            request.setAttribute("batchError", "Aucun fichier Excel fourni.");
            request.getRequestDispatcher("/secretaire/profil").forward(request, response);
            return;
        }

        // 2) Déterminer l'extension pour préserver le type du fichier.
        String submittedName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String extension = "";
        int dotIndex = submittedName.lastIndexOf('.');
        if (dotIndex >= 0) {
            extension = submittedName.substring(dotIndex);
        }

        // 3) Stocker le fichier en local avec un nom horodaté (dossier src/main/resources/inscritExcel).
        Path targetDir = Paths.get("src/main/resources/inscritExcel");
        Files.createDirectories(targetDir);

        String storedName = System.currentTimeMillis() + extension;
        Path targetFile = targetDir.resolve(storedName);

        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, targetFile, StandardCopyOption.REPLACE_EXISTING);
        }

        // 4) Lecture du fichier avec Apache POI et création des comptes via InscriptionExcelService.
        try {
            int createdCount = new InscriptionExcelService().importerUtilisateurs(targetFile.toAbsolutePath().toString()).size();
            request.setAttribute("batchSuccess", "Import terminé : " + createdCount + " comptes créés.");
        } catch (Exception e) {
            request.setAttribute("batchError", "Erreur lors de l'import : " + e.getMessage());
        }

        request.getRequestDispatcher("/secretaire/profil").forward(request, response);
    }
}
