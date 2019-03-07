package enum_test;

public enum Weekend {
    MONDAY{
        public String getDesc() {
            return null;
        }
    }, TUESDAY {
        public String getDesc() {
            return null;
        }
    }, WEDNESDAY {
        public String getDesc() {
            return null;
        }
    }, THURSDAY {
        public String getDesc() {
            return null;
        }
    }, FRIDAY {
        public String getDesc() {
            return null;
        }
    }, SATURDAY {
        public String getDesc() {
            return null;
        }
    }, SUNDAY {
        public String getDesc() {
            return null;
        }
    };

    public abstract String getDesc();

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
