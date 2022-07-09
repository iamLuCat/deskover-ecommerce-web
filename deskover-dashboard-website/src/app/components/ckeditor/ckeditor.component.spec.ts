import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CKEditorComponent } from './ckeditor.component';

describe('CkeditorComponent', () => {
  let component: CKEditorComponent;
  let fixture: ComponentFixture<CKEditorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CKEditorComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CKEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
