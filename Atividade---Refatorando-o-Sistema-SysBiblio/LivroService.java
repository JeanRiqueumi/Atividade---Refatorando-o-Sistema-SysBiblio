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

    public void editar(int indice, Livro novosDados) throws Exception {
        if (indice < 0 || indice >= acervo.size()) {
            throw new Exception("Livro não encontrado para edição.");
        }
        validarLivro(novosDados);

        Livro livro = acervo.get(indice);
        livro.setTitulo(novosDados.getTitulo().trim().toUpperCase());
        livro.setAutor(novosDados.getAutor().trim().toUpperCase());
        livro.setAnoPublicacao(novosDados.getAnoPublicacao());
        livro.setNumeroPaginas(novosDados.getNumeroPaginas());

    }
// Método privado para validar os dados do livro antes de cadastrar ou editar
    private void validarLivro(Livro livro) throws Exception {
        if (livro == null)
            throw new Exception("Objeto Nulo");

        if (livro.getTitulo() == null || livro.getTitulo().isBlank())
            throw new Exception("Título inválido!!!");

        if (livro.getAutor() == null || livro.getAutor().isBlank())
            throw new Exception("Autor inválido!!!");

        if (livro.getNumeroPaginas() <= 0)
            throw new Exception("O livro deve ter pelo menos uma página!");

        if (livro.getAnoPublicacao() < 1500 || livro.getAnoPublicacao() > 2026)
            throw new Exception("Ano de publicação inválido!");
    }
// Novos métodos de pesquisa e estatísticas
    public List<Livro> pesquisarPorAutor(String autor) {
        List<Livro> livrosEncontrados = new ArrayList<>();
        autor = autor.toUpperCase();

        for (Livro livro : acervo) {
            if (livro.getAutor().contains(autor))
                livrosEncontrados.add(livro);
        }
        return livrosEncontrados;

    }

    public List<Livro> pesquisarPorAno(int ano) {
        List<Livro> livrosEncontrados = new ArrayList<>();

        for (Livro livro : acervo) {
            if (livro.getAnoPublicacao() == ano)
                livrosEncontrados.add(livro);
        }
        return livrosEncontrados;
    }

    public List<Livro> gerarRelatorioEstatisticas(){
        List<Livro> relatorio = new ArrayList<>(acervo);
        relatorio.sort((l1, l2) -> Integer.compare(l2.getNumeroPaginas(), l1.getNumeroPaginas()));
        return relatorio;

    }
    public int somarTotalPaginas() {
        return acervo.stream().mapToInt(Livro::getNumeroPaginas).sum();
    }
    public double calcularMediaPaginas() {
        return acervo.isEmpty() ? 0 : (double) somarTotalPaginas() / acervo.size();
    }
}
