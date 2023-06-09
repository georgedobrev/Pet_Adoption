package mapper;

import com.example.persistence.binding.ShelterAddBindingModel;
import com.example.persistence.entities.AnimalsEntity;
import com.example.persistence.entities.ShelterPhoneEntity;
import com.example.persistence.entities.SheltersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = MapperUtil.class)
public interface ShelterMapper {
    @Mapping(target = "shelterName", source = "model.shelterName")
    @Mapping(target = "shelterCity", source = "model.shelterCity")
    @Mapping(target = "shelterAddress", source = "model.shelterAddress")
    @Mapping(target = "shelterEmail", source = "model.shelterEmail")
    @Mapping(target = "shelterPhone", source = "shelterPhone")
    SheltersEntity toShelterEntity(ShelterAddBindingModel model, ShelterPhoneEntity shelterPhone);


}
