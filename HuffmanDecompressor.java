import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class HuffmanDecompressor {

    public void decompress(String compressedPath, String outputPath) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(compressedPath));
        Map<Character, Integer> frequencies = (Map<Character, Integer>) in.readObject();
        String binaryData = (String) in.readObject();
        in.close();

        HuffmanNode root = buildTree(frequencies);
        StringBuilder result = new StringBuilder();
        HuffmanNode current = root;

        for (char bit : binaryData.toCharArray()) {
            current = (bit == '0') ? current.left : current.right;
            if (current.isLeaf()) {
                result.append(current.symbol);
                current = root;
            }
        }

        Files.write(new File(outputPath).toPath(), result.toString().getBytes());
        System.out.println("Archivo descomprimido como " + outputPath);
    }

    private HuffmanNode buildTree(Map<Character, Integer> frequencies) {
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
}
