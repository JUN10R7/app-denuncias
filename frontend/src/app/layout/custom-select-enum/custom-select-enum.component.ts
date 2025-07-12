import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-custom-select-enum',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './custom-select-enum.component.html',
  styleUrls: ['./custom-select-enum.component.scss']
})
export class CustomSelectEnumComponent {
  @Input() items: any[] = [];
  @Input() selectedId?: number | string;
  @Input() placeholder: string = 'Selecciona una opción';

  @Output() selectionChange = new EventEmitter<number | string>();

  dropdownAbierto = false;

  toggleDropdown() {
    this.dropdownAbierto = !this.dropdownAbierto;
  }

  seleccionar(item: any) {
    this.selectionChange.emit(item.id);
    this.dropdownAbierto = false;
  }

  get selectedItem() {
    return this.items.find(i => i.id === this.selectedId);
  }
}