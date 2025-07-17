import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditInputDialogComponent } from './edit-input-dialog.component';

describe('EditInputDialogComponent', () => {
  let component: EditInputDialogComponent;
  let fixture: ComponentFixture<EditInputDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EditInputDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditInputDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
