import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditAutocompleteDialog } from './edit-autocomplete-dialog';

describe('EditAutocompleteDialog', () => {
  let component: EditAutocompleteDialog;
  let fixture: ComponentFixture<EditAutocompleteDialog>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EditAutocompleteDialog]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditAutocompleteDialog);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
