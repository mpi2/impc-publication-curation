import { TestBed, inject } from '@angular/core/testing';

import { PublicationService } from './publication.service';

describe('PublicationsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PublicationService]
    });
  });

  it('should be created', inject([PublicationService], (service: PublicationService) => {
    expect(service).toBeTruthy();
  }));
});
