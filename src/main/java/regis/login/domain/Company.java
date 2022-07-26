package regis.login.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "company")
public class Company {
    private String companyTitle;

    public String getCompany() {
        return companyTitle;
    }

    public void setCompany(String company) {
        this.companyTitle = company;
    }

}
