package main.api.response;


public class InitResponseDto {

    private String title = "DevPub";
    private String subtitle = "Blog";
    private String phone = "333";
    private String email = "mail@mail.ru";
    private String copyright = "opensource";
    private String copyrightFrom = "2022";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getCopyrightFrom() {
        return copyrightFrom;
    }

    public void setCopyrightFrom(String copyrightFrom) {
        this.copyrightFrom = copyrightFrom;
    }

}

//{
//        "title":"DevPub",
//        "subtitle":"Рассказы разработчиков",
//        "phone":"+7 903 666-44-55",
//        "email":"mail@mail.ru",
//        "copyright":"Дмитрий Сергеев",
//        "copyrightFrom":"2005"
//        }