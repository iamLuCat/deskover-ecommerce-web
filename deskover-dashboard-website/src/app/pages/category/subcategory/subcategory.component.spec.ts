import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubcategoryComponent } from './subcategory.component';

describe('SubcategoryComponent', () => {
  let component: SubcategoryComponent;
  let fixture: ComponentFixture<SubcategoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubcategoryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubcategoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
