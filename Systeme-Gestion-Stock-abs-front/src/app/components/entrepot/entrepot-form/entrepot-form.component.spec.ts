import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EntrepotFormComponent } from './entrepot-form.component';

describe('EntrepotFormComponent', () => {
  let component: EntrepotFormComponent;
  let fixture: ComponentFixture<EntrepotFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EntrepotFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EntrepotFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
