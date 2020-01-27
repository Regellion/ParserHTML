import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.util.Scanner;

public class Main
{
    private static final String htmlLink = "https://lenta.ru/";

    public static void main(String[] args) {
        System.out.println("Введите папку для скачивания изображений: \n" +
                "Не забывайте в конце пути поставить '\\'");

        Scanner scanner = new Scanner(System.in);
        String downloadPath = scanner.nextLine();
        try {
            // получаем лист ссылок
            Elements linksList = downloadImg();
            // создаем картинки
            writeFiles(linksList, downloadPath);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    // Метод получения абсолютных путей изображений
    private static Elements downloadImg() throws IOException {
            Document document = Jsoup.connect(htmlLink).get();
            return document.select("img.g-picture");
    }
    // Метод создания файлов по ссылкам
    private static void writeFiles(Elements elements, String downloadPath) throws IOException {
        for(int i = 0; i < elements.size(); i++) {
            // К исени файла прибавляю сразу формат в котором будет сохранена картинка
            String name = i + ".jpg";
            URL url = new URL(elements.get(i).absUrl("src"));
            InputStream in = url.openStream();
            OutputStream out = new BufferedOutputStream(new FileOutputStream( downloadPath + name));
            for (int b; (b = in.read()) != -1;) {
                out.write(b);
            }
            out.close();
            in.close();
        }
    }
}
