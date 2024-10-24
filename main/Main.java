// Classe que define o nó da árvore rubro-negra
class No {
    int valor;
    No pai;
    No esquerda;
    No direita;
    boolean cor; // true = VERMELHO, false = PRETO
 
    // Construtor para criar um novo nó
    public No(int valor) {
        this.valor = valor;
        this.cor = true; // Novo nó sempre inserido como vermelho (regra 1)
        this.pai = null; // Nó pai
        this.esquerda = null; 
        this.direita = null;
    }
}

// Classe da Árvore Rubro-Negra
class ArvoreRubroNegra {
    private No raiz;
    private final No NULO;

    // Construtor que inicializa o nó NULO e a raiz 
    public ArvoreRubroNegra() {
        NULO = new No(0); 
        NULO.cor = false; // NÓ NULO sempre é preto
        NULO.esquerda = null; 
        NULO.direita = null;
        raiz = NULO;
    }

    // Método de inserção 
    public void inserir(int valor) {
        // Cria um novo nó com o valor passado e o insere na árvore
        No novoNo = new No(valor);
        novoNo.esquerda = NULO;
        novoNo.direita = NULO;
        
        No pai = null; // Inicializa o pai como nulo  
        No atual = raiz; // Inicializa o nó atual como a raiz

        // Encontrar o local apropriado para inserir o novo nó
        while (atual != NULO) {
            pai = atual;
            if (novoNo.valor < atual.valor) {
                atual = atual.esquerda;
            } else {
                atual = atual.direita;
            }
        }

        // Configura o pai do novo nó e o insere na árvore
        novoNo.pai = pai;
        if (pai == null) {
            raiz = novoNo; // Se a árvore estiver vazia (novo nó é a raiz)
        } else if (novoNo.valor < pai.valor) { // Se o valor do novo nó for menor que o pai
            pai.esquerda = novoNo; // Insere à esquerda
        } else {
            pai.direita = novoNo;
        }

        // Se o novo nó for a raiz, torna-o preto
        if (novoNo.pai == null) {
            novoNo.cor = false;
            return;
        }

        // Se o avô não existir, retorna (não há necessidade de balancear)
        if (novoNo.pai.pai == null) {
            return;
        }

        // Balancear a árvore após a inserção 
        balancearInsercao(novoNo);
    }

    // Função para corrigir a árvore rubro-negra após a inserção
    private void balancearInsercao(No k) {
        No tio;
        while (k.pai.cor == true) { // Enquanto o pai for vermelho
            if (k.pai == k.pai.pai.esquerda) { // Caso o pai esteja à esquerda do avô
                tio = k.pai.pai.direita; // Tio é o filho direito do avô
                if (tio.cor == true) { // Caso o tio também seja vermelho
                    // Caso 1: Recolorir
                    k.pai.cor = false; // Pai se torna preto
                    tio.cor = false; // Tio se torna preto
                    k.pai.pai.cor = true; // Avô se torna vermelho
                    k = k.pai.pai; // Subimos para o avô
                } else {
                    // Caso 2: O nó é filho direito
                    if (k == k.pai.direita) {
                        k = k.pai;
                        rotacaoEsquerda(k); // Rotaciona à esquerda
                    }
                    // Caso 3: O nó é filho esquerdo
                    k.pai.cor = false;
                    k.pai.pai.cor = true;
                    rotacaoDireita(k.pai.pai); // Rotaciona à direita
                }
            } else { // Caso o pai esteja à direita do avô (simétrico ao caso anterior)
                tio = k.pai.pai.esquerda;
                if (tio.cor == true) {
                    // Caso 1: Recolorir
                    k.pai.cor = false;
                    tio.cor = false;
                    k.pai.pai.cor = true;
                    k = k.pai.pai;
                } else {
                    // Caso 2: O nó é filho esquerdo
                    if (k == k.pai.esquerda) {
                        k = k.pai;
                        rotacaoDireita(k); // Rotaciona à direita
                    }
                    // Caso 3: O nó é filho direito
                    k.pai.cor = false;
                    k.pai.pai.cor = true;
                    rotacaoEsquerda(k.pai.pai); // Rotaciona à esquerda
                }
            }
            if (k == raiz) {
                break;
            }
        }
        raiz.cor = false; // A raiz deve sempre ser preta
    }

    // Método para rotacionar à esquerda
    private void rotacaoEsquerda(No x) {
        No y = x.direita; // 'y' é o filho direito de 'x'
        x.direita = y.esquerda; // O filho esquerdo de 'y' se torna o filho direito de 'x'
        if (y.esquerda != NULO) {
            y.esquerda.pai = x; // Ajusta o pai do novo filho esquerdo
        }
        y.pai = x.pai; // Ajusta o pai de 'y'
        if (x.pai == null) {
            raiz = y; // Se 'x' era a raiz, agora 'y' será a nova raiz
        } else if (x == x.pai.esquerda) {
            x.pai.esquerda = y; // Ajusta o filho esquerdo do pai de 'x'
        } else {
            x.pai.direita = y; // Ajusta o filho direito do pai de 'x'
        }
        y.esquerda = x; // 'x' se torna o filho esquerdo de 'y'
        x.pai = y; // Ajusta o pai de 'x'
    }
    
    // Método para rotacionar à direita
    private void rotacaoDireita(No x) {
        No y = x.esquerda; // 'y' é o filho esquerdo de 'x'
        x.esquerda = y.direita; // O filho direito de 'y' se torna o filho esquerdo de 'x'
        if (y.direita != NULO) {
            y.direita.pai = x; // Ajusta o pai do novo filho direito
        }
        y.pai = x.pai; // Ajusta o pai de 'y'
        if (x.pai == null) {
            raiz = y; // Se 'x' era a raiz, agora 'y' será a nova raiz
        } else if (x == x.pai.direita) {
            x.pai.direita = y; // Ajusta o filho direito do pai de 'x'
        } else {
            x.pai.esquerda = y; // Ajusta o filho esquerdo do pai de 'x'
        }
        y.direita = x; // 'x' se torna o filho direito de 'y'
        x.pai = y; // Ajusta o pai de 'x'
    }
    
    // Método para imprimir a árvore (ordem em-ordem)
    public void imprimirArvore(No no, int espacos) {
        if (no == NULO) {
            return; // Não imprime nós nulos (folhas)
        }

        espacos += 5;

        // Imprime a subárvore direita
        imprimirArvore(no.direita, espacos);

        // Espaçamento para alinhamento visual
        System.out.println();
        for (int i = 5; i < espacos; i++) {
            System.out.print(" ");
        }

        // Imprime o valor do nó e sua cor
        String corNo = no.cor ? "Vermelho" : "Preto";
        System.out.println(no.valor + "(" + corNo + ")");

        // Imprime a subárvore esquerda
        imprimirArvore(no.esquerda, espacos);
    }


    public No getRaiz() {
        return raiz;
    }
}

// Classe principal para testar a árvore rubro-negra
public class Main {
    public static void main(String[] args) {
        ArvoreRubroNegra arvore = new ArvoreRubroNegra();

        // Inserindo nós na árvore
        arvore.inserir(8);
        arvore.inserir(18);
        arvore.inserir(5);
        arvore.inserir(15);
        arvore.inserir(17);
        arvore.inserir(25);
        arvore.inserir(40);
        arvore.inserir(80);

        // Imprimindo a árvore após as inserções de forma mais legível
        arvore.imprimirArvore(arvore.getRaiz(), 0);
    }
}
