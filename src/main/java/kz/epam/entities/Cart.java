package kz.epam.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart extends Entity implements Serializable {

    private List<LineItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public List<LineItem> getItems() {
        return items;
    }

    public void setItems(List<LineItem> items) {
        this.items = items;
    }

//    public int getCount()
//    {
//        return items.size();
//    }

    public double getTotal() {

        double total = 0.00;
        for (int i=0; i<items.size(); i++)
        {
            LineItem item = items.get(i);
            total += item.getItemTotal();
        }
        return total;
    }
    public void addItem(LineItem item)
    {
        //If the item already exists in the cart, only the quantity is changed.
        String code = item.getProduct().getCode();
        int quantity = item.getQuantity();
        for (int i = 0; i<items.size(); i++)
        {
            LineItem lineItem = items.get(i);
            if (lineItem.getProduct().getCode().equals(code))
            {
                quantity++;
                lineItem.setQuantity(quantity);
                return;
            }
        }
        items.add(item);
    }

    public void removeItem(LineItem item)
    {
        String code = item.getProduct().getCode();
        for (int i = 0; i<items.size(); i++)
        {
            LineItem lineItem = items.get(i);
            if (lineItem.getProduct().getCode().equals(code))
            {
                items.remove(i);
//                return;
            }
        }
    }
}
