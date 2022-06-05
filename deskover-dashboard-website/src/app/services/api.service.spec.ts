import {TestBed} from '@angular/core/testing';

import {RestApiService} from './rest-api.service';

describe('ApiService', () => {
    let service: RestApiService;

    beforeEach(() => {
        TestBed.configureTestingModule({});
        service = TestBed.inject(RestApiService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
