package eu.getmangos.mapper;

import java.util.Date;

import org.mapstruct.Mapper;

import eu.getmangos.dto.CorpseType;
import eu.getmangos.dto.CorpseDTO;
import eu.getmangos.entities.Corpse;

@Mapper(componentModel = "cdi")
public interface CorpseMapper {

    CorpseDTO map(Corpse entity);

    Corpse map(CorpseDTO dto);

    default Date map(Long time) {
        return new Date(time);
    }

    default Long map(Date date) {
        return date.getTime();
    }

    default CorpseType map(Byte type) {
        return CorpseType.convert(type);
    }

    default Byte map(CorpseType type) {
        return type.code;
    }
}
