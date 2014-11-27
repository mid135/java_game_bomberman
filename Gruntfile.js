module.exports = function (grunt) {

    grunt.initConfig({
        watch: {
            fest: {
                files: ['public_html/templates/*.xml'],
                tasks: ['fest'],
                options: {
                    interrupt: true,
                    atBegin: true
                }
            }
        },
        server: {
                files: [
                    'public_html/js/**/*.js', /* следим за статикой */
                    'public_html/css/**/*.css',
                    'public_html/index.html'
                ],
                options: {
                    interrupt: true,
                    livereload: true /* перезагрузить страницу */
                }
        },
        shell: {
            options: {
                stdout: true,
                stderr: true
            }//,
           // server: {
                //command: 'java -cp ./out/artifacts/server_jar/server.jar main.Main 8080'
                //command:  'java -cp ./target/L1.1-1.0-jar-with-dependencies.jar servlets.Main 8000'
//TODO что то не так со сборкой сервера в идее
                //command: 'mvn compile assembly:single'
                //command: 'java -cp ./out/artifacts/server_jar/server.jar servlets.Main 8000'
            //}
        },
        fest: {
            templates: {
                files: [{
                    expand: true,
                    cwd: 'public_html/templates',
                    src: '*.xml',
                    dest: 'public_html/js/tmpl'
                }],
                options: {
                    template: function (data) {
                        return grunt.template.process(
                            'define(function () { return <%= contents %> ; });',
                            {data: data}
                        );
                    }
                }
            }
        },
        concurrent: {
            target: ['watch', 'shell'],
            options: {
                logConcurrentOutput: true /* Вывод логов */
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-concurrent');
    grunt.loadNpmTasks('grunt-shell');
    grunt.loadNpmTasks('grunt-fest');
    

    grunt.registerTask('default', ['concurrent']);
};

   