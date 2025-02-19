import java.io.*;
import java.util.*;

public class FileUtils {

    public static List<File> listarArquivosEntrada(String caminhoPasta) {
        File pasta = new File(caminhoPasta);
        File[] arquivos = pasta.listFiles((dir, name) -> name.endsWith(".txt"));
        return arquivos != null ? Arrays.asList(arquivos) : new ArrayList<>();
    }

    public static List<Bombeiro> lerArquivoEntrada(File arquivo) throws IOException {
        List<Bombeiro> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                lista.add(new Bombeiro(partes[0].trim(), Integer.parseInt(partes[1]), Integer.parseInt(partes[2]), Integer.parseInt(partes[3])));
            }
        }
        return lista;
    }

    public static void escreverArquivoSaida(String caminho, Map<String, Map<String, String[][]>> escalas) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            for (String nomeArquivo : escalas.keySet()) {
                bw.write("===== ESCALA PARA " + nomeArquivo + " =====\n");

                for (String tarefa : escalas.get(nomeArquivo).keySet()) {
                    bw.write(tarefa + "\n");
                    bw.write(String.join("_____", Escala.DIAS_DA_SEMANA) + "\n");

                    for (int turno = 0; turno < 2; turno++) {
                        for (int dia = 0; dia < 7; dia++) {
                            bw.write(escalas.get(nomeArquivo).get(tarefa)[dia][turno] + "____");
                        }
                        bw.write("\n");
                    }
                    bw.write("\n");
                }
                bw.write("\n");
            }
        }
    }
}
