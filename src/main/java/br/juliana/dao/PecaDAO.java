package br.juliana.dao;

import br.juliana.model.Peca;
import java.util.ArrayList;
import java.util.List;

public class PecaDAO {
    // Lista simulada em memória (ou conectada ao banco se preferir)
    private static List<Peca> bancoPeças = new ArrayList<>();
    private static long geradorId = 1;

    public void cadastrar(Peca peca) {
        peca.setId(geradorId++);
        bancoPeças.add(peca);
        System.out.println("✅ Peça cadastrada com sucesso!");
    }

    // Resolve o erro do 'listarTodas'
    public List<Peca> listarTodas() {
        return bancoPeças;
    }

    // Resolve o erro do 'buscarPorTermo'
    public List<Peca> buscarPorTermo(String termo) {
        List<Peca> resultados = new ArrayList<>();
        for (Peca p : bancoPeças) {
            if (p.getNomeComponente().toLowerCase().contains(termo.toLowerCase()) ||
                    String.valueOf(p.getId()).equals(termo)) {
                resultados.add(p);
            }
        }
        return resultados;
    }
}