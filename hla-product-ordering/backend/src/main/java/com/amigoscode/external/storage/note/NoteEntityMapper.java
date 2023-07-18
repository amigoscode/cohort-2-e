package com.amigoscode.external.storage.note;

import com.amigoscode.domain.note.Note;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoteEntityMapper {

    Note toDomain(NoteEntity entity);

    NoteEntity toEntity(Note domain);
}
