package hernanszel.junkMaker.utils;

public class RequestContextDTO {

    private String country;
    private String provider;
    private String channel;

    private String shoppingCartId;
    private String transactionId;

    private String productId;
    private String productType;

    @Override
    public String toString() {
        return "hernanszel.junkMaker.Utils.RequestContextDTO{" +
                "country='" + country + '\'' +
                ", provider='" + provider + '\'' +
                ", channel='" + channel + '\'' +
                ", shoppingCartId='" + shoppingCartId + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", productId='" + productId + '\'' +
                ", productType='" + productType + '\'' +
                '}';
    }
}