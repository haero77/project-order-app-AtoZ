package devcourse.baemin.domain.menu;

import java.util.UUID;

public class MenuDto {

    public static class AdditionDto {

        private String menuName;
        private long price;
        private String description;

        public AdditionDto() {
        }

        public AdditionDto(String menuName, long price) {
            this.menuName = menuName;
            this.price = price;
        }

        public AdditionDto(String menuName, long price, String description) {
            this.menuName = menuName;
            this.price = price;
            this.description = description;
        }

        public String getMenuName() {
            return menuName;
        }

        public long getPrice() {
            return price;
        }

        public String getDescription() {
            return description;
        }
    }

    public static class ResponseDto {

        private final UUID menuId;
        private final String menuName;
        private final long price;
        private final String description;
        private final UUID storeId;

        public ResponseDto(Menu menu) {
            this.menuId = menu.getMenuId();
            this.menuName = menu.getMenuName();
            this.price = menu.getPrice();
            this.description = menu.getDescription();
            this.storeId = menu.getStoreId();
        }

        public UUID getMenuId() {
            return menuId;
        }

        public String getMenuName() {
            return menuName;
        }

        public long getPrice() {
            return price;
        }

        public String getDescription() {
            return description;
        }

        public UUID getStoreId() {
            return storeId;
        }
    }
}