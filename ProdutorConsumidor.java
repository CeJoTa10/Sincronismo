import java.util.concurrent.ArrayBlockingQueue;

public class ProdutorConsumidor {

    private static final ArrayBlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(5);

    public static void main(String[] args) {
        System.out.println("=== SIMULADOR PRODUTOR E CONSUMIDOR ===");

        Thread threadProdutor = new Thread(new Runnable() {
            @Override
            public void run() {
                int item = 1;
                while (true) {
                    try {
                        Thread.sleep(1000); 
                        buffer.put(item);   
                        System.out.println("PRODUTOR colocou o item: " + item);
                        exibirBuffer();
                        item++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread threadConsumidor = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1500);  // Espera 1.5 segundos
                        int item = buffer.take(); // Retira o item (espera se estiver vazio)
                        System.out.println("CONSUMIDOR retirou o item: " + item);
                        exibirBuffer();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        threadProdutor.start();
        threadConsumidor.start();
    }

    private static void exibirBuffer() {
        System.out.println("Estado atual do Buffer: " + buffer);
        System.out.println("Quantidade de itens: " + buffer.size() + "/5");
        System.out.println("----------------------------------------");
    }
}