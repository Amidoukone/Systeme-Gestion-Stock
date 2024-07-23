import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EntrepotListComponent } from './entrepot-list.component';

describe('EntrepotListComponent', () => {
  let component: EntrepotListComponent;
  let fixture: ComponentFixture<EntrepotListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EntrepotListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EntrepotListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
