package app.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Nasa
{
    private String copyright;

    private LocalDate date;

    private String explanation;
    private String hdurl;

    private String mediaType;

    private String serviceVersion;
    private String title;
    private String url;
}
