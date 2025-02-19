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

                    
                    boolean correto = verificarEscala(escala.getEscala(), bombeiros);
                    System.out.println("Verificação: " + (correto ? "Saída correta" : "Saída Errada"));
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

    
    private static boolean verificarEscala(Map<String, String[][]> escala, List<Bombeiro> bombeiros) {
        
        Map<String, Integer> contagemReal = new HashMap<>();

        
        for (Bombeiro b : bombeiros) {
            contagemReal.put(b.nome + "_Incêndio", 0);
            contagemReal.put(b.nome + "_Socorro", 0);
            contagemReal.put(b.nome + "_Telefone", 0);
        }

        
        for (String tarefa : escala.keySet()) {
            String[][] matriz = escala.get(tarefa);
            for (String[] dia : matriz) {
                for (String nome : dia) {
                    if (contagemReal.containsKey(nome + "_" + tarefa)) {
                        contagemReal.put(nome + "_" + tarefa, contagemReal.get(nome + "_" + tarefa) + 1);
                    }
                }
            }
        }

        
        for (Bombeiro b : bombeiros) {
            if (contagemReal.get(b.nome + "_Incêndio") != b.incendios ||
                contagemReal.get(b.nome + "_Socorro") != b.socorros ||
                contagemReal.get(b.nome + "_Telefone") != b.telefones) {
                return false; 
            }
        }

        return true; 
    }
}
