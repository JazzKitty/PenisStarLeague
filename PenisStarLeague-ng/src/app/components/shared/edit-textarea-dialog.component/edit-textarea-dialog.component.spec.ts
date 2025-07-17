import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditTextareaDialogComponent } from './edit-textarea-dialog.component';

describe('EditTextareaDialogComponent', () => {
  let component: EditTextareaDialogComponent;
  let fixture: ComponentFixture<EditTextareaDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EditTextareaDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditTextareaDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
