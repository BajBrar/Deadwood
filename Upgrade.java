class Upgrade {
    int level;
    String currency;
    int amount;
    
    public Upgrade(int level, String currency, int amount) {
        this.level = level;
        this.currency = currency;
        this.amount = amount;
    }
    
    //will allow for the upgrade to be printed out when needed
    @Override
    public String toString() {
        return "Level: " + level + ", Currency: " + currency + ", Amount: " + amount;
    }
}
