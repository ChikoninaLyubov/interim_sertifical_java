import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

public class ToyStoreDrawing {
    public static void main(String[] args) throws IOException {
        String toyIds = "1 2 3";
        String toyNames = "constructor robot doll";
        String toyWeights = "3 2 6";
        ToyStoreDrawing toyStore = new ToyStoreDrawing(toyIds, toyNames, toyWeights);
        toyStore.writeToFile("output.txt");
    }
    


    private static final int MAX_TOYS = 10;
    private static final double[] WEIGHTS = {0.2, 0.2, 0.6}; //вес для каждого типа игрушек
    private PriorityQueue<Toy> toyQueue;

    public ToyStoreDrawing(String toyIds, String toyNames, String toyWeights) {
        toyQueue = new PriorityQueue<>(MAX_TOYS, Comparator.comparingInt(Toy::getWeight));
        String[] ids = toyIds.split(" ");
        String[] names = toyNames.split(" ");
        String[] weights = toyWeights.split(" ");
        for (int i = 0; i < ids.length && i < MAX_TOYS; i++) {
            int id = Integer.parseInt(ids[i]);
            String name = i < names.length ? names[i] : "";
            int weight = i < weights.length ? Integer.parseInt(weights[i]) : 0;
            toyQueue.add(new Toy(id, name, weight));
        }
    }

    public int get() {
        Random random = new Random();
        double rand = random.nextDouble();
        double weightSum = 0.0;
        for (int i = 0; i < WEIGHTS.length; i++) {
            weightSum += WEIGHTS[i];
            if (rand < weightSum) {
                if (i == 0) {
                    return 1;
                } else if (i == 1) {
                    return 2;
                } else {
                    return 3;
                }
            }
        }
        return 3; // возвращает по умолчанию
    }

    public void writeToFile(String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        for (int i = 0; i < 10; i++) {
            int toyId = get();
            Toy toy = toyQueue.stream()
                .filter(t -> t.getId() == toyId)
                .findFirst()
                .orElse(null);
            if (toy != null) {
                String result = String.format("%d %s\n", toy.getId(), toy.getName());
                writer.write(result);
            }
        }
        writer.close();
    }

    private static class Toy {
        private int id;
        private String name;
        private int weight;

        public Toy(int id, String name, int weight) {
            this.id = id;
            this.name = name;
            this.weight = weight;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getWeight() {
            return weight;
        }
    }
}

    

