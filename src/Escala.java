import java.util.*;

public class Escala {
    public static final String[] DIAS_DA_SEMANA = {"DOM", "SEG", "TER", "QUA", "QUI", "SEX", "SAB"};
    private Map<String, String[][]> escala;
    private final List<Bombeiro> bombeiros;
    private Map<String, Integer> alocacoesRestantes;

    public Escala(List<Bombeiro> bombeiros) {
        this.bombeiros = bombeiros;
        inicializarEscala();
    }

    private void inicializarEscala() {
        escala = new HashMap<>();
        escala.put("Incêndio", new String[7][2]);
        escala.put("Socorro", new String[7][2]);
        escala.put("Telefone", new String[7][2]);

        for (String chave : escala.keySet()) {
            for (int i = 0; i < 7; i++) {
                Arrays.fill(escala.get(chave)[i], "----");
            }
        }

        alocacoesRestantes = new HashMap<>();
        for (Bombeiro b : bombeiros) {
            alocacoesRestantes.put(b.nome + "_Incêndio", b.incendios);
            alocacoesRestantes.put(b.nome + "_Socorro", b.socorros);
            alocacoesRestantes.put(b.nome + "_Telefone", b.telefones);
        }
    }

    public boolean gerarEscala() {
        return backtrack(0, 0, "Incêndio") &&
               backtrack(0, 0, "Socorro") &&
               backtrack(0, 0, "Telefone");
    }

    private boolean backtrack(int dia, int turno, String tarefa) {
        if (dia == 7) return true; 

        for (Bombeiro b : bombeiros) {
            String chave = b.nome + "_" + tarefa;

            if (alocacoesRestantes.get(chave) > 0) {
                
                escala.get(tarefa)[dia][turno] = b.nome;
                alocacoesRestantes.put(chave, alocacoesRestantes.get(chave) - 1);

                
                if (turno == 1) {
                    if (backtrack(dia + 1, 0, tarefa)) return true;
                } else {
                    if (backtrack(dia, 1, tarefa)) return true;
                }

                
                escala.get(tarefa)[dia][turno] = "----";
                alocacoesRestantes.put(chave, alocacoesRestantes.get(chave) + 1);
            }
        }
        return false;
    }

    public Map<String, String[][]> getEscala() {
        return escala;
    }
}
