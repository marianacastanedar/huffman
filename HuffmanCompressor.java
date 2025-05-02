import java.io.*;
import java.nio.file.Files;
import java.util.*;


public class HuffmanCompressor {
    private Map<Character, String> huffmanCodes = new HashMap<>();
    private Map<Character, Integer> frequencies = new HashMap<>();

    public void compress(String inputPath, String outputPath) throws IOException {
        String text = new String(Files.readAllBytes(new File(inputPath).toPath()));
        countFrequencies(text);
        HuffmanNode root = buildTree();
        generateCodes(root, "");

        System.out.println("Símbolos y porcentajes:");
        int total = text.length();
        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            double porcentaje = 100.0 * entry.getValue() / total;
            System.out.printf("'%c' => %d veces (%.2f%%)%n", entry.getKey(), entry.getValue(), porcentaje);
        }

        StringBuilder binaryText = new StringBuilder();
        for (char c : text.toCharArray()) {
            binaryText.append(huffmanCodes.get(c));
        }

        // Guardar archivo comprimido
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(outputPath))) {
            out.writeObject(frequencies);
            out.writeObject(binaryText.toString());
        }

        System.out.println("Archivo comprimido guardado como " + outputPath);
    }

    private void countFrequencies(String text) {
        for (char c : text.toCharArray()) {
            frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
        }
    }

    private HuffmanNode buildTree() {
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            queue.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        while (queue.size() > 1) {
            HuffmanNode n1 = queue.poll();
            HuffmanNode n2 = queue.poll();
            HuffmanNode combined = new HuffmanNode(n1.frequency + n2.frequency, n1, n2);
            queue.add(combined);
        }

        return queue.poll();
    }

    private void generateCodes(HuffmanNode node, String code) {
        if (node.isLeaf()) {
            huffmanCodes.put(node.symbol, code);
            return;
        }
        generateCodes(node.left, code + "0");
        generateCodes(node.right, code + "1");
    }
}
