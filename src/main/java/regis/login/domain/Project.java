package regis.login.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Document(collection = "project")
public class Project {
    @Indexed(unique = true, direction= IndexDirection.DESCENDING, dropDups = true)

    @Id
//    private String id;
    private String projectTitle;

    private String projectDesc;

    private String addedBy;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date endDate;

    private String projectCode;

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getProjectCode() {
        return projectCode;
    }

    static String usingUUID() {
        UUID randomUUID = UUID.randomUUID();
        return randomUUID.toString().replaceAll("-", "");
    }
    public void setProjectCode() {
        String randomString = usingUUID();
        randomString = randomString.substring(0, 6);
        this.projectCode = randomString;
    }


    private int totalHrs;

    public int getTotalHrs() {
        return totalHrs;
    }

    public void setTotalHrs(int totalHrs) {
        this.totalHrs = totalHrs;
    }

    public String getProjectDesc() {
        return this.projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
            this.projectDesc = projectDesc;
    }
}
