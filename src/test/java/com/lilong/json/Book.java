package com.lilong.json;
import lombok.Data;

/**
 * 类名称：Book<br>
 * 类描述：<br>
 * 创建时间：2018年04月19日<br>
 *
 * @author lichao
 * @version 1.0.0
 */

/**
 * @author itguang
 * @create 2017-12-10 11:01
 **/
@Data
public class Book {

    /**
     *    "author": "Nigel Rees",
     "price": 8.95,
     "category": "reference",
     "title": "Sayings of the Century"
     */
    private String author;
    private Double price;
    private String category;
    private String title;

}