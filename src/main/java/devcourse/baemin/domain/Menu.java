package devcourse.baemin.domain;

import devcourse.baemin.domain.value.Amount;

import java.text.MessageFormat;
import java.util.UUID;

public class Menu {

    private final UUID menuId;
    private final String storeName;
    private final String menuName;
    private final Amount price;
    private String description;

    public Menu(UUID menuId, Store store, String menuName, Amount price) {
        this.menuId = menuId;
        this.storeName = store.getStoreName();
        this.menuName = menuName;
        this.price = price;
    }

    public UUID getMenuId() {
        return this.menuId;
    }

    public String getStoreName() {
        return this.storeName;
    }

    public String getMenuName() {
        return this.menuName;
    }

    public long getPrice() {
        return this.price.getAmount();
    }

    public String getDescription() {
        if (this.description == null) {
            throw new IllegalStateException(
                    MessageFormat.format("No description exists for menu ''{0}''", this.menuName)
            );
        }
        return this.description;
    }
}
