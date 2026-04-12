import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LivroService {

    private List<Livro> acervo = new ArrayList<>();

    public void cadastrar(Livro novoLivro) throws Exception {

        if (novoLivro == null)
            throw new Exception("Objeto Nulo");
        // Estou validando o Título
        if (novoLivro.getTitulo() == null || novoLivro.getTitulo().isEmpty())
            throw new Exception("Título inválido!!!");
        // Estou formatando o Título
        novoLivro.setTitulo(novoLivro.getTitulo().trim().toUpperCase());

        // TODO fazer mesma validação e formatação para Autor

        if (novoLivro.getAnoPublicacao() < 1900
                || novoLivro.getAnoPublicacao() > LocalDate.now().getYear())
            throw new Exception("Ano de publicação inválido");
        for (Livro livro : acervo) {
            if (livro.getTitulo().equalsIgnoreCase(novoLivro.getTitulo())
                    && livro.getAutor().equalsIgnoreCase(novoLivro.getAutor())
                    && livro.getAnoPublicacao() == novoLivro.getAnoPublicacao())
                throw new Exception("Já existe livro cadastrado com este Título, Autor e Ano de publicação");
        }

        // Nesta parte estaria chamando a camada Repository
        // Neste exemplo não usaremos Repositórios
        acervo.add(novoLivro);

    }

    public List<Livro> listar() {
        // ordenar
        return acervo;
    }

    public List<Livro> pesquisar(String titulo) {
        List<Livro> livrosEncontrados = new ArrayList<>();
        titulo = titulo.toUpperCase();

        for (Livro livro : acervo) {
            if (livro.getTitulo().contains(titulo))
                livrosEncontrados.add(livro);
        }
        return livrosEncontrados;
    }

    public void remover(int indice) throws Exception {
        if (indice < 0 || indice >= acervo.size()) {
            throw new Exception("Livro não encontrado para o número informado.");
        }

        acervo.remove(indice);
    }

    public List<Livro> listarOrdenadoPorAutor() {
        List<Livro> listaOrdenada = new ArrayList<>(acervo);

        listaOrdenada.sort((l1, l2) -> l1.getAutor().compareToIgnoreCase(l2.getAutor()));

        return listaOrdenada;
    }

}
