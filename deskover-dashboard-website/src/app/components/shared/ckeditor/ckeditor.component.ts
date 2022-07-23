import {Component, forwardRef, Input, OnInit} from '@angular/core';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from "@angular/forms";
import * as Editor from 'ckeditor5-custom-build/build/ckeditor';


@Component({
  selector: 'app-ckeditor',
  templateUrl: './ckeditor.component.html',
  styleUrls: ['./ckeditor.component.scss'],
  providers: [{
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => CKEditorComponent),
      multi: true
  }]
})
export class CKEditorComponent implements OnInit, ControlValueAccessor {
  public Editor = Editor;
  public config: any;

  @Input() id: string;
  @Input() name: string;

  private _value: string = '';

  get value() {
    return this._value;
  }

  set value(value: string) {
    this._value = value;
    this.onChange(value);
  }

  constructor() {
    this.config = {
      removePlugins: [
        'Title',
        'Markdown',
      ],
      toolbar: {
        items: [
          'sourceEditing', '|',
          'undo', 'redo', '|',
          'heading', '|',
          'bold', 'italic', 'strikethrough', 'underline', 'code', 'subscript', 'superscript', 'removeFormat', '|',
          'findAndReplace', 'selectAll', '-',
          'bulletedList', 'numberedList', 'todoList', '|',
          'outdent', 'indent', '|',
          'fontSize', 'fontFamily', 'fontColor', 'fontBackgroundColor', 'highlight', '|',
          'alignment', '|',
          'specialCharacters', 'horizontalLine', 'pageBreak', '-',
          'link', 'insertImage', 'blockQuote', 'insertTable', 'mediaEmbed', 'codeBlock', 'htmlEmbed', '|',
        ],
        shouldNotGroupWhenFull: true,
      },
      language: 'vi',
      image: {
        toolbar: [
          'toggleImageCaption', 'imageTextAlternative', "linkImage", '|',
          'imageStyle:wrapText', 'imageStyle:breakText', '|',
          "resizeImage"
        ]
      },
      table: {
        contentToolbar: [
          'tableRow', 'tableColumn', 'mergeTableCells', '|',
          'tableProperties', 'tableCellProperties', 'toggleTableCaption', '|',
        ]
      },
      mediaEmbed: {
        toolbar: [
          'mediaEmbed'
        ]
      },
      htmlEmbed: {
        showPreviews: true
      },
      fontSize: {
        options: [10, 12, 14, 'default', 18, 20, 22, 24, 26, 28, 36, 48, 72],
        supportAllValues: true
      },
      mention: {
        feeds: [
          {
            marker: '@',
            feed: [
              '@Nguyễn Hoài Minh', '@Phạm Văn Hải', '@Phạm Quang Vũ'
            ],
            minimumCharacters: 1
          }
        ]
      },
      heading: {
        options: [
          {model: 'paragraph', title: 'Paragraph', class: 'ck-heading_paragraph'},
          {model: 'heading1', view: 'h1', title: 'Heading 1', class: 'ck-heading_heading1'},
          {model: 'heading2', view: 'h2', title: 'Heading 2', class: 'ck-heading_heading2'},
          {model: 'heading3', view: 'h3', title: 'Heading 3', class: 'ck-heading_heading3'},
          {model: 'heading4', view: 'h4', title: 'Heading 4', class: 'ck-heading_heading4'},
          {model: 'heading5', view: 'h5', title: 'Heading 5', class: 'ck-heading_heading5'},
          {model: 'heading6', view: 'h6', title: 'Heading 6', class: 'ck-heading_heading6'}
        ]
      }
    }
  }

  ngOnInit(): void {
  }

  onChange(value: string) {
  }

  onTouch() {
  }

  onReady(editor) {
    if (editor.model.schema.isRegistered('image')) {
      editor.model.schema.extend('image', { allowAttributes: 'blockIndent' });
    }
  }

  writeValue(obj: any): void {
    this._value = obj;
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouch = fn;
  }

}
