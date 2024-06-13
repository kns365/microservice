import {Directive, ElementRef, HostListener, Inject, Input, LOCALE_ID, OnInit} from '@angular/core';
import {formatCurrency, formatNumber} from '@angular/common';
import {NgControl} from '@angular/forms';

@Directive({
  selector: '[format-number]'
})
export class FormatNumberDirective implements OnInit {
  format = 'N0';
  digitsInfo = '1.0-0';
  @Input() currency = '$';
  @Input() sufix = '';
  @Input() decimalCharacter = null;

  @Input('format') set _(value: string) {
    this.format = value;
    if (this.format == 'N2') this.digitsInfo = '1.2-2';

    const parts = value.split(':');
    if (parts.length > 1) {
      this.format = value[0];
      this.digitsInfo = parts[1];
    }
  }

  @HostListener('blur', ['$event.target']) blur(target: any) {
    target.value = this.parse(target.value);
  }

  @HostListener('focus', ['$event.target']) focus(target: any) {
    target.value = this.control.value;
  }

  ngOnInit() {
    setTimeout(() => {
      this.el.nativeElement.value = this.parse(this.el.nativeElement.value);
    });
  }

  constructor(
    @Inject(LOCALE_ID) private locale: string,
    private el: ElementRef,
    private control: NgControl
  ) {
  }

  parse(value: any) {
    let newValue = value;

    if (this.format == 'C2')
      newValue = formatCurrency(value, this.locale, this.currency);
    if (this.format == 'N2')
      newValue = formatNumber(value, this.locale, this.digitsInfo);
    if (this.format == 'N0')
      newValue = formatNumber(value, this.locale, this.digitsInfo);
    if (this.format == 'NX')
      newValue = formatNumber(value, this.locale, this.digitsInfo);
    if (this.decimalCharacter)
      return (
        newValue.replace('.', 'x').replace(',', '.').replace('x', ',') +
        this.sufix
      );

    return newValue + this.sufix;
  }

}
