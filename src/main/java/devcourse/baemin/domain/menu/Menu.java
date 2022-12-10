package devcourse.baemin.domain.menu;

import devcourse.baemin.global.value.Amount;

import java.util.UUID;

public class Menu {

    private final UUID menuId;
    private final String menuName;
    private final Amount price;
    private final UUID storeId;
    private String description;

    public Menu(UUID menuId, String menuName, Amount price, UUID storeId) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.price = price;
        this.storeId = storeId;
    }

    public Menu(UUID menuId, String menuName, Amount price, String description, UUID storeId) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.price = price;
        this.description = description;
        this.storeId = storeId;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public long getPrice() {
        return price.getAmount();
    }

    public String getDescription() {
        return description;
    }

    public UUID getStoreId() {
        return storeId;
    }
}
