import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MotifFormComponent } from './motif-form.component';

describe('MotifFormComponent', () => {
  let component: MotifFormComponent;
  let fixture: ComponentFixture<MotifFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MotifFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MotifFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
