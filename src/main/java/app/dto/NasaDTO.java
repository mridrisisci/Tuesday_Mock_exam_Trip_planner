package app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NasaDTO
{
    private String copyright;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String explanation;
    private String hdurl;

    @JsonProperty("media_type")
    private String mediaType;

    @JsonProperty("service_version")
    private String serviceVersion;
    private String title;
    private String url;

    public NasaDTO(NasaDTO nasaDTO)
    {
        this.copyright = nasaDTO.getCopyright();
        this.date = nasaDTO.getDate();
        this.explanation = nasaDTO.getExplanation();
        this.hdurl = nasaDTO.getHdurl();
        this.mediaType = nasaDTO.getMediaType();
        this.serviceVersion = nasaDTO.getServiceVersion();
        this.title = nasaDTO.getTitle();
        this.url = nasaDTO.getUrl();
    }
}
