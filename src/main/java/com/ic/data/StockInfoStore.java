/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ic.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


public class StockInfoStore {

    private static StockInfoStore stockInfo = null;
    private HashMap<String, String> stockMap;

    private StockInfoStore() {

        this.stockMap = new HashMap<String, String>();

        BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(StockInfoStore.class.getResourceAsStream("/stocks.csv")));
        String line;
        try {
            line = reader.readLine();

            while (line != null) {
                String data[] = line.split(",");
                if (data.length >= 2) {
                    String code = data[0];
                    code = "00000" + code;
                    code = code.substring(code.length() - 5);
                    String name = data[1];
                    stockMap.put(code, name);
                    //System.out.println(code + " " + name);
                    Logger.getLogger(StockInfoStore.class.getName()).log(Level.FINE, "Stock " + code + " " + name);
                }

                line = reader.readLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(StockInfoStore.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static synchronized StockInfoStore getInstance() {
        if (stockInfo == null) {
            stockInfo = new StockInfoStore();
        }
        return stockInfo;
    }

    public String getStockName(String code) {
        code = "00000" + code;
        code = code.substring(code.length() - 5);
        String name = stockMap.get(code);
        if (name == null) return code;
        else return name;

    }
}
