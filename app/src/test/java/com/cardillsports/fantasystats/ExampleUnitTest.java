package com.cardillsports.fantasystats;

import com.cardillsports.fantasystats.fantasyv.model.Scoreboard;
import com.cardillsports.fantasystats.fantasyv.model.User;
import com.cardillsports.fantasystats.fantasyv.network.JsonParser;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import static org.junit.Assert.assertNotNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void testJsonParser() throws Exception {
        File file = new File("test.json");
        FileReader fileReader = new FileReader(file);
        Scanner scanner = new Scanner(file);
        String line = scanner.nextLine();
        System.out.println(line);
        assertNotNull(line);

        FileInputStream fin = new FileInputStream(file);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();

        Scoreboard scoreboard = JsonParser.createScoreboard(ret);
        assertNotNull(scoreboard);
    }

    @Test
    public void testJsonParserGames() throws Exception {
        File file = new File("getGamesTest.json");
        Scanner scanner = new Scanner(file);
        String line = scanner.nextLine();
        System.out.println(line);
        assertNotNull(line);

        FileInputStream fin = new FileInputStream(file);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();

        User user = JsonParser.createUser(ret);
        assertNotNull(user);

    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

}