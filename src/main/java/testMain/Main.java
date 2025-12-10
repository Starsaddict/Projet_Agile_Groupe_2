package testMain;

import java.util.Arrays;
import java.util.List;

import service.GroupeService;
import service.groupeService;

public class Main {
    public static void main(String[] args) {
        groupeService groupeService = new groupeService();

        List<Long> joueursIds = Arrays.asList(1L, 2L, 3L);

        groupeService.creerGroupe("Equipe A", joueursIds);
    }
}
