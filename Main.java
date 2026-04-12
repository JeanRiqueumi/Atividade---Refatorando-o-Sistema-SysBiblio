import java.util.List;

//Dependências
LivroService service = new LivroService();

void main() {
    String menu = """
            ===== SysBiblio =====
            1 - Cadastrar Livro
            2 - Listar Livros
            3 - Pesquisar Livro
            4 - Remover Livro
            5 - Listar Livros por Autor (A-Z)
            0 - Sair
            """;

    int opcao;
    do {
        IO.println(menu);
        opcao = Input.scanInt("Digite uma opção: ");
        try {
            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> listar();
                case 3 -> pesquisar();
                case 4 -> remover();
                case 5 -> listarPorAutor();
                case 0 -> IO.println("Até breve!!!");
                default -> IO.println("Opção Inválida");
            }
        } catch (Exception e) {
            IO.println("ERRO: " + e.getMessage());
        }
        IO.readln("Pressione Enter para continuar...");
    } while (opcao != 0);
}

void cadastrar() throws Exception {
    String titulo = Input.scanString("Digite o título do livro: ");
    String autor = Input.scanString("Digite o autor do livro: ");
    int anoPublicacao = Input.scanInt("Digite o ano de publicação do livro: ");
    int numeroPaginas = Input.scanInt("Digite o número de páginas do livro: ");

    Livro novoLivro = new Livro(titulo, autor, anoPublicacao, numeroPaginas);

    service.cadastrar(novoLivro);

    IO.println("Livro cadastrado com sucesso!!!");
}

void listar() {

    List<Livro> livros = service.listar();

    imprimirLista(livros);

}

void pesquisar() {

    String pesquisa = Input.scanString("Digite parte do título: ");

    List<Livro> livros = service.pesquisar(pesquisa);

    imprimirLista(livros);
}

void remover() throws Exception {
    List<Livro> livros = service.listar();

    if (livros.isEmpty()) {
        IO.println("O acervo está vazio. Nada para remover.");
        return;
    }

    imprimirLista(livros);

    int numero = Input.scanInt("Digite o número do livro que deseja remover: ");

    service.remover(numero - 1);

    IO.println("Livro removido com sucesso!");
}

void listarPorAutor() {
    IO.println("--- Livros Ordenados por Autor ---");

    List<Livro> livros = service.listarOrdenadoPorAutor();

    imprimirLista(livros);
}

void imprimirLista(List<Livro> livros) {
    if (livros.isEmpty()) {
        IO.println("Nenhum livro encontrado!");
        return;
    }

    int i = 1;
    for (Livro livro : livros) {
        IO.println(i++ + " - " + livro);
        // IO.println(i++ + " - " + livro.toString());
    }
}