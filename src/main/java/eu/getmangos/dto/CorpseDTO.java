package eu.getmangos.dto;

import java.util.Date;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import lombok.Setter;
import lombok.Getter;

/**
 * Death System
 */
@Setter @Getter
public class CorpseDTO {

  @Schema(description = "The character global unique identifier of the corpse.")
  private Integer guid;

  @Schema(description = "The character global unique identifier (See characters.guid).")
  private Integer player;

  @Schema(description = "The x position of the character's corpse location.")
  private Float positionX;

  @Schema(description = "The y position of the character's corpse location.")
  private Float positionY;

  @Schema(description = "The z position of the character's corpse location.")
  private Float positionZ;

  @Schema(description = "The orientation of the corpse. (North = 0.0, South = 3.14159).")
  private Float orientation;

  @Schema(description = "The map ID the corpse is in. (See maps.dbc)")
  private Integer map;

  @Schema(description = "The time of death")
  private Date time;

  @Schema(description = "The display type of the corpse.")
  private CorpseType corpseType;

  @Schema(description = "The instance ID that the corpse is in.")
  private Integer instance;
}