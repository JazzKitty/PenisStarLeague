import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PopupDialog } from './popup-dialog';

describe('PopupDialog', () => {
  let component: PopupDialog;
  let fixture: ComponentFixture<PopupDialog>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PopupDialog]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PopupDialog);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
