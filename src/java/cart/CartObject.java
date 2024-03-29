//Tạo application constants 
//Sửa bookID ở viewcart, truyền DTO vào CartObject

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cart;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import product.ProductDAO;
import product.ProductDTO;

/**
 *
 * @author N.Minh
 */
public class CartObject implements Serializable {
//    private Map<String, Integer> items;

    private Map<ProductDTO, Integer> items;

//    public Map<String, Integer> getItems() {
//        return items;
//    }
    public Map<ProductDTO, Integer> getItems() {
        return items;
    }

//    public void addItemToCart(String itemID, int quantity){
    public void addItemToCart(String itemID, int quantity) {
        //1. check item exists
        if (itemID == null) {
            return;
        }
        if (itemID.trim().isEmpty()) {
            return;
        }
        if (quantity <= 0) {
            return;
        }
        //2. Check items existed
        if (this.items == null) {
            this.items = new HashMap<>();
        }

        try {
            ProductDAO dao = new ProductDAO();
            ProductDTO dto = dao.getBookInfoByID(itemID);
            if (this.items.containsKey(searchDTO(itemID))) {
                quantity = quantity + searchItemsValue(itemID);
                this.items.put(searchDTO(itemID), quantity);
            }else{
                this.items.put(dto, quantity);
            }
        } catch (NamingException ex) {
            Logger.getLogger(CartObject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CartObject.class.getName()).log(Level.SEVERE, null, ex);
        }

//        //1. check item exists
//        if (itemID == null) {
//            return;
//        }
//        if (itemID.trim().isEmpty()) {
//            return;
//        }
//        if (quantity <= 0) {
//            return;
//        }
//        //2. Check items existed
//        if (this.items == null) {
//            this.items = new HashMap<>();
//        }
//        //3. Check item existed
//        if (this.items.containsKey(itemID)) {
//            quantity = quantity + this.items.get(itemID);
//        }
//        
//        //Get item to items
//        this.items.put(itemID, quantity);
    }

    private boolean compareProductDTO(ProductDTO productDTO) {
        boolean check = false;
        for (ProductDTO key : items.keySet()) {
            if (key.getId().equals(productDTO.getId())) {
                check = true;
                return check;
            }
        }
        return check;
    }

//    public void removeItemFromCart(String itemID, int quantity){
    public void removeItemFromCart(String itemID, int quantity) {
        //1. check item exists
        if (itemID == null) {
            return;
        }
        if (itemID.trim().isEmpty()) {
            return;
        }

        //2. Check items existed
        if (this.items == null) {
            return;
        }
        //3. Check item existed
        ProductDTO dto = searchDTO(itemID);
        if (this.items.containsKey(dto)) {
            this.items.remove(dto);
            if (this.items.isEmpty()) {
                this.items = null;
            }
        }
//        if (this.items.containsKey(itemID)) {
//            this.items.remove(itemID);
//            if (this.items.isEmpty()) {
//                this.items = null;
//            }
//        }
    }

    private ProductDTO searchDTO(String itemID) {
        ProductDTO productDTO = new ProductDTO();
        for (ProductDTO item : items.keySet()) {
            if (item.getId().equals(itemID)) {
                productDTO = item;
                return productDTO;
            }
        }
        return productDTO;
    }
    
    private int searchItemsValue(String itemID) {
        int num = 0;
        for (Map.Entry<ProductDTO, Integer> entry : items.entrySet()) {
            ProductDTO key = entry.getKey();
            Integer value = entry.getValue();
            if (key.getId().equals(itemID)) {
                num = value;
                return num;
            }
        } 
        return num;
    }
}
