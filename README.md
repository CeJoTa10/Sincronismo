# 🔄 Projeto Sincronismo: Produtor / Consumidor em Java

Este projeto foi desenvolvido para a disciplina de **Sistemas Operacionais** com o objetivo de demonstrar, na prática, o problema clássico de **Sincronização de Processos/Threads: Produtor/Consumidor** (Buffer Limitado).

---

## 📌 O Problema do Produtor/Consumidor

Em Sistemas Operacionais, quando múltiplos processos ou threads compartilham uma mesma região de memória (o **Buffer**), é necessário controlar o acesso para evitar dois problemas principais:

1. **Buffer Cheio (Overflow):** O Produtor tenta inserir um novo item em um buffer sem espaço disponível.
2. **Buffer Vazio (Underflow):** O Consumidor tenta remover um item de um buffer que não possui nenhum dado.

---

## 🥖 Entendendo a Lógica (A Analogia da Padaria)

Para facilitar o entendimento, pense no funcionamento do programa como uma **Padaria**:

* 👨‍🍳 **Produtor = Padeiro:** Produz os pães e coloca no balcão.
* 🥐 **Item = Pão:** O dado gerado pela thread produtora.
* 📦 **Buffer = Prateleira (Capacidade: 5 pães):** A área de memória compartilhada.
* 🚶 **Consumidor = Cliente:** Compra os pães e retira do balcão.

### As Regras do Sincronismo:
* **Prateleira Cheia (5/5):** O Padeiro cruza os braços e **espera** até que um cliente compre um pão.
* **Prateleira Vazia (0/5):** O Cliente senta e **espera** até que o Padeiro termine de assar um novo pão.

---

## 💻 Código-Fonte (`ProdutorConsumidor.java`)

```java
import java.util.concurrent.ArrayBlockingQueue;

public class ProdutorConsumidor {

    // Buffer compartilhado com capacidade máxima de 5 itens
    private static final ArrayBlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(5);

    public static void main(String[] args) {
        System.out.println("=== SIMULADOR PRODUTOR E CONSUMIDOR ===");

        // Thread do Produtor (Padeiro)
        Thread threadProdutor = new Thread(new Runnable() {
            @Override
            public void run() {
                int item = 1;
                while (true) {
                    try {
                        Thread.sleep(1000); // Demora 1 segundo para produzir um item
                        buffer.put(item);    // Bloqueia a thread se o buffer estiver cheio!
                        System.out.println("PRODUTOR colocou o item: " + item);
                        exibirBuffer();
                        item++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // Thread do Consumidor (Cliente)
        Thread threadConsumidor = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1500);  // Demora 1.5 segundos para consumir um item
                        int item = buffer.take(); // Bloqueia a thread se o buffer estiver vazio!
                        System.out.println("CONSUMIDOR retirou o item: " + item);
                        exibirBuffer();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // Inicia o funcionamento simultâneo das threads
        threadProdutor.start();
        threadConsumidor.start();
    }

    // Exibe a quantidade e o estado do buffer no terminal
    private static void exibirBuffer() {
        System.out.println("Estado atual do Buffer: " + buffer);
        System.out.println("Quantidade de itens: " + buffer.size() + "/5");
        System.out.println("----------------------------------------");
    }
}