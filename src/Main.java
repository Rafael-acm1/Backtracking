import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String pastaEntrada = "src/entradas";
        String arquivoSaida = "saida.txt";

        try {
            List<File> arquivosEntrada = FileUtils.listarArquivosEntrada(pastaEntrada);
            if (arquivosEntrada.isEmpty()) {
                System.out.println("Nenhum arquivo encontrado na pasta " + pastaEntrada);
                return;
            }

            Map<String, Map<String, String[][]>> todasAsEscalas = new LinkedHashMap<>();

            for (File arquivo : arquivosEntrada) {
                System.out.println("Processando: " + arquivo.getName());

                List<Bombeiro> bombeiros = FileUtils.lerArquivoEntrada(arquivo);
                Escala escala = new Escala(bombeiros);

                if (escala.gerarEscala()) {
                    todasAsEscalas.put(arquivo.getName(), escala.getEscala());
                } else {
                    System.out.println("Não foi possível gerar escala para " + arquivo.getName());
                }
            }

            FileUtils.escreverArquivoSaida(arquivoSaida, todasAsEscalas);
            System.out.println("Todas as escalas foram geradas e salvas em " + arquivoSaida);

        } catch (IOException e) {
            System.err.println("Erro ao processar arquivos: " + e.getMessage());
        }
    }
}
