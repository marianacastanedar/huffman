import java.io.File;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        HuffmanCompressor compressor = new HuffmanCompressor();
        HuffmanDecompressor decompressor = new HuffmanDecompressor();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el nombre del archivo que desea comprimir: ");
        String inputFileName = scanner.nextLine();
        File inputFile = new File(inputFileName);

        if (!inputFile.exists()) {
            System.out.println("Error: El archivo '" + inputFileName + "' no se está");
            return;
        }

        String compressedFile = "comprimido.huff";
        String decompressedFile = "salida.txt";

        try {
            compressor.compress(inputFileName, compressedFile);
            decompressor.decompress(compressedFile, decompressedFile);
        } catch (Exception e) {
            System.out.println("Error pruebe de nuevo");
            e.printStackTrace();
        }
    }
}
