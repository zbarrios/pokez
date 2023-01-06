package com.pokez.configuration;

import com.pokez.repository.TypeRepository;
import org.springframework.context.ApplicationEvent;

public class TypeRepositoryEvent extends ApplicationEvent {

  private TypeRepository typeRepository;

  public TypeRepositoryEvent(TypeRepository typeRepository) {
    super(typeRepository);
    this.typeRepository = typeRepository;
  }

  public TypeRepository getTypeRepository() {
    return typeRepository;
  }

  @Override
  public Object getSource() {
    return super.getSource();
  }
}
