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
            6 - Exibir Estatísticas do Acervo
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
                case 6 -> exibirEstatisticas();
                case 0 -> IO.println("Até breve!!!");
                default -> IO.println("Opção Inválida");
            }
        } catch (Exception e) {
            IO.println("ERRO: " + e.getMessage());
        }
        if (opcao != 0) {
            IO.readln("Pressione Enter para continuar...");
        }
    } while (opcao != 0);
}

void cadastrar() throws Exception {
    String titulo = Input.scanString("Digite o título do livro: ");
    if (titulo.equals("0")) return;
    String autor = Input.scanString("Digite o autor do livro: ");
    if (autor.equals("0")) return;
    int anoPublicacao = Input.scanInt("Digite o ano de publicação do livro: ");
    if (anoPublicacao == 0) return;
    int numeroPaginas = Input.scanInt("Digite o número de páginas do livro: ");
    if (numeroPaginas == 0) return;

    Livro novoLivro = new Livro(titulo, autor, anoPublicacao, numeroPaginas);

    service.cadastrar(novoLivro);

    IO.println("Livro cadastrado com sucesso!!!");
}

void listar() {

    List<Livro> livros = service.listar();

    imprimirLista(livros);

}

void pesquisar() {
    IO.println("""
            \n--- Opções de Pesquisa ---
            1 - Por Título (ou parte dele)
            2 - Por Autor
            3 - Por Ano de Publicação
            0 - Voltar ao menu principal
            """);

    int opcaoPesquisa = Input.scanInt("Escolha uma opção: ");

    if (opcaoPesquisa == 0)
        return;

    List<Livro> livrosEncontrados = new ArrayList<>();

    switch (opcaoPesquisa) {
        case 1 -> {
            String titulo = Input.scanString("Digite o título: ");
            livrosEncontrados = service.pesquisar(titulo);
        }
        case 2 -> {
            String autor = Input.scanString("Digite o nome do autor: ");
            // Chamamos o novo método que criaremos no Service
            livrosEncontrados = service.pesquisarPorAutor(autor);
        }
        case 3 -> {
            int ano = Input.scanInt("Digite o ano: ");
            // Chamamos o novo método que criaremos no Service
            livrosEncontrados = service.pesquisarPorAno(ano);
        }
        case 0 -> {
            return;
        }
        default -> IO.println("Opção inválida!");
    }

    imprimirLista(livrosEncontrados);
}

void remover() throws Exception {
    List<Livro> livros = service.listar();

    if (livros.isEmpty()) {
        IO.println("O acervo está vazio. Nada para remover.");
        return;
    }

    imprimirLista(livros);

    int numero = Input.scanInt("Digite o número do livro que deseja remover (0 para cancelar): ");
if (numero == 0){
    IO.println("Operação de remoção cancelada.");
    return;
}
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

// Novo método para editar um livro existente
void editar() throws Exception {
    listar();
    int numero = Input.scanInt("Digite o número do livro que deseja editar: ");

    if (numero == 0)
        return;

    String titulo = Input.scanString("Novo título: ");
    String autor = Input.scanString("Novo autor: ");
    int ano = Input.scanInt("Novo ano: ");
    int paginas = Input.scanInt("Novo número de páginas: ");

    Livro novosDados = new Livro(titulo, autor, ano, paginas);

    service.editar(numero - 1, novosDados);

    IO.println("Livro editado com sucesso!");
}

// Novo método para exibir estatísticas do acervo
void exibirEstatisticas() {

    List<Livro> relatorio = service.gerarRelatorioEstatisticas();
    if (relatorio.isEmpty()) {
        IO.println("O acervo está vazio no momento.");
        return;
    }

    IO.println("\n====================================================");
    IO.println("            ESTATÍSTICAS DO ACERVO                 ");
    IO.println("====================================================");

    // Dados consolidados
    IO.println(" Total de Livros: " + relatorio.size());
    IO.println(" Total de Páginas: " + service.somarTotalPaginas());
    IO.println(String.format(" Média de Páginas: %.2f", service.calcularMediaPaginas()));
    IO.println("----------------------------------------------------");

    // Cabeçalho da Lista
    IO.println(String.format("%-4s | %-20s | %-10s | %-5s", "POS", "TÍTULO", "AUTOR", "PÁG."));
    IO.println("----------------------------------------------------");

    // Loop para imprimir os livros formatados
    int pos = 1;
    for (Livro l : relatorio) {
        String linha = String.format("%02dº  | %-20.20s | %-10.10s | %-5d",
                pos++,
                l.getTitulo(),
                l.getAutor(),
                l.getNumeroPaginas());
        IO.println(linha);
    }

    IO.println("====================================================");
    IO.println("Pressione Enter para continuar...");
}
