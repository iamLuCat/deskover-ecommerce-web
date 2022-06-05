import { TestBed } from '@angular/core/testing';

import { FirebaseApiService } from './firebase-api.service';

describe('FirebaseApiService', () => {
  let service: FirebaseApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FirebaseApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
